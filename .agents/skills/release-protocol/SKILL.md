---
name: release-protocol
description: Publish AllayMC/Protocol releases to Maven Central, verify both modules and their POMs, run the existing GitHub Actions release workflow, and advance the next SNAPSHOT version. Use for requests such as "release Protocol 1.26.50-R1"; version queries and code synchronization do not authorize publication.
---

# Protocol Releases

Use this skill for the independent AllayMC/Protocol repository. A request to release a version authorizes the version change, commits, pushes, and existing CI publication workflow. For preparation or inspection requests, stay within the requested scope.

## Repository conventions

- Work from the repository root containing this skill and confirm that `origin` points to `AllayMC/Protocol`.
- The release version is the `version=` entry in the root `gradle.properties`.
- Both modules share the same version: `org.allaymc.protocol:bedrock-codec` and `org.allaymc.protocol:bedrock-connection`.
- `.github/workflows/gradle.yml` builds and publishes on pushes to `main`. Actions secrets supply publishing credentials and signing keys.
- The established workflow uses a release commit followed by a commit advancing the next snapshot. It does not use version tags or GitHub Releases; create those only when requested.
- Git commits and CI record the release process. Use normal project documentation and test names; do not add release-task checklists or aggregate test classes named after the task.

Read the current workflow, Gradle publishing configuration, and recent version commits before starting to confirm these conventions still apply.

## Prepare the release

1. Check the branch, local changes, and remote updates. Release from the verified contents of `main` and preserve unrelated local changes. Upstream synchronization and changes to other repositories are outside this workflow.
2. Use the requested release version, such as `1.26.50-R1`. Check version history, corresponding CI runs, and Maven Central for an existing or pending publication. A public repository 404 does not rule out a version that is still being distributed.
3. Set the release version in `gradle.properties`, then run:

   ```sh
   ./gradlew clean build :bedrock-codec:generatePomFileForMavenPublication :bedrock-connection:generatePomFileForMavenPublication
   ```

4. Inspect each module's `build/publications/maven/pom-default.xml`: the group must be `org.allaymc.protocol`, the artifact ID must match the module, and the version must match the requested release. The connection module's codec dependency must use that release version too.
5. Review the change scope and commit the version file using the repository's `Release <version>` subject format. Record the full release SHA. Commit source or workflow fixes separately.

## Publish through CI

Use the Maven Publish Plugin's automatic release tasks. Submit both modules in one Gradle invocation so they share a deployment:

```sh
./gradlew :bedrock-codec:publishAndReleaseToMavenCentral :bedrock-connection:publishAndReleaseToMavenCentral
```

For releases, `publishToMavenCentral` uploads by default and requires a subsequent publish action in Central Portal. `publishAndReleaseToMavenCentral` requests automatic publication. For SNAPSHOT versions, the plugin uploads directly to the snapshot repository. Reuse the existing CI configuration; if it still uses upload-only tasks, make the smallest change needed to complete the release.

When the plugin version or configuration changes, consult the [official publishing documentation](https://vanniktech.github.io/gradle-maven-publish-plugin/central/) or the matching source version. Automatic publication may wait only for validation. A `PUBLISHING` log entry does not mean public downloads are available.

Push the release commit to `origin/main` and check CI for that exact full SHA. Confirm the build, signing, upload, and automatic publication request succeeded. An earlier successful run is not evidence for this release. Use publishing credentials through the existing Actions secrets; do not read or print their plaintext values.

## Maven Central waiting policy

The user explicitly prefers not to keep watching Maven Central because distribution to the public repository can be slow.

- Stop checking Maven Central once CI has successfully requested automatic publication and Central has passed validation or entered the publishing state.
- By default, do not poll public POM/JAR URLs, Central Portal, or mirrors, and do not create monitors, scheduled follow-ups, or notifications.
- Keep the plugin's default validation level. Do not set `DeploymentValidation.PUBLISHED` merely to wait for public download availability.
- If a one-time download check returns 404, report "publication submitted; public repository distribution is still pending" and stop waiting for the 404 to disappear.
- Report "available for download" only after verifying the download URL. Wait for availability when the user explicitly requests that verification.

## Advance the next version

After release CI confirms that the publication request succeeded, advance development to the next revision on the same version line: `1.26.50-R1` becomes `1.26.50-R2-SNAPSHOT`. Follow any different next version specified by the user.

Create a separate commit such as `The next version is 1.26.50-R2` and push it to `main`. This triggers normal snapshot CI. Distinguish the release SHA from the subsequent development SHA and report their actual statuses separately.

## Failures and repeated requests

- Build or signing failure: fix the reported problem and validate again. Retry when no upload has occurred.
- Uncertain upload or publication outcome: inspect CI for the original SHA, the deployment ID, and its status before retrying. Avoid creating duplicate deployments.
- Validation failure: address the specific validation errors. If recovery requires a new version, an account action by the user, or clarification of whether the original deployment was published, explain the blocker and request the necessary information.
- Published release versions cannot be overwritten. For a repeated request for the same version, report the existing publication and CI instead of uploading again.

## Delivery

Briefly report the release version, both module coordinates or links, the release commit and CI link, and the next development version. Distinguish "automatic publication submitted" from "available in the public repository." Distribution delay alone is not a publication failure.
