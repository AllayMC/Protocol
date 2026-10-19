# Protocol Development

AllayMC/Protocol is an independent hard fork of CloudburstMC/Protocol. Use English for repository instructions, documentation, and comments.

## Upstream changes must become normal project code

When adopting upstream changes, adapt the implementation to this repository's Java 21 conventions. Compiling an upstream patch is not sufficient. Read the corresponding local models and serializers before resolving differences.

- Rewrite incoming Java 8 idioms to match the surrounding project code. Use records for immutable data, record accessors, switch expressions or arrow cases where appropriate, and pattern matching instead of redundant casts. Preserve mutable packet models where serialization requires mutation.
- Use the fork's `bedrock.util` utilities, item helpers, raw `String` text APIs, `PrimitiveShapes` models, and immutable `SerializedSkin` builder. Preserve NetEase extensions and existing version-specific behavior.
- Check constructor argument order, nullability, defaults, and enum/type-map IDs during adaptation. Record component order can differ from wire field order. Retain usable existing constructors when extending data models.
- Preserve individual upstream commits where practical with `git cherry-pick -x`, including the original author, author date, subject, co-author trailers, and source SHA. Keep migration accounting and adaptation explanations in Git history.
- Do not create synchronization-task tests such as `UpstreamSyncSerializationTest`, aggregate tests organized around an import batch, or migration ledgers such as `UPSTREAM_SYNC.md`. Merely renaming such a test does not fix its scope.
- Keep the same task-specific terminology and scaffolding out of production code. Name classes, helpers, and fields for their protocol or runtime purpose. Remove temporary `// new` markers, migration narration, and conversational notes.
- Add or adapt tests when needed to verify the affected behavior. Organize them around the actual packet, codec, serializer, data type, or connection component. For wire-format changes, check independent byte layouts or fixtures where appropriate; a round trip alone can hide matching encoder and decoder mistakes.

Review the complete imported range before finishing, including files that merged without conflicts. Preserve unrelated local changes and formatting. Run the relevant Gradle checks for the completed implementation.

## Protocol version documentation is required

Maintain version documentation as part of every protocol change, including upstream migrations. Build and test success do not establish that these annotations are complete.

- Mark a field introduced in a later protocol version with Javadoc `@since vNNN`. Use the protocol version, not the Maven artifact version. Follow this existing convention rather than inventing a runtime annotation.
- For record components, provide `@param` documentation and include the component's introduction version when it differs from the type's baseline. When converting an upstream class to a record, carry over its field descriptions, introduction versions, and deprecation versions.
- Give new protocol-specific packet and data types a type-level `@since`. Fields present from that type's introduction can use the type's baseline; fields added later need their own version information. Apply the same rule to nested types and enum values.
- When a newer codec stops using a field, document the cutoff version and its continued use by older codecs in Javadoc, using `@deprecated since vNNN` or the record component's `@param` description. Protocol retirement is documentation-only: do not add Java `@Deprecated` annotations for it, since the field remains valid for older codecs and an API-wide deprecation warning can mislead callers. Treat unrelated Java API deprecations separately.
- Document changes in optionality, defaults, or meaning across versions even when the field name remains unchanged. Distinguish absent values from `false`, zero, and empty strings where the wire format does.
- Put Javadoc block tags after the descriptive prose. Do not insert `@since` or `@deprecated` into the middle of a sentence.

Determine version boundaries from serializer reads and writes, inheritance, helper overrides, codec registration, type maps, and commit history. Check upstream annotations against that evidence and fill gaps when upstream omitted them. A helper method can exist before a codec makes it reachable. A Java field rename does not imply a new wire field. When preview codecs are renamed, resolve the historical introduction version before changing existing tags.

Before completing a migration, inspect every affected packet and data declaration across the full requested range: ordinary fields, record components, nested types, enum entries, constructor parameter documentation, and deprecated members. Verify both introduction and retirement versions. Review the resulting documentation in context instead of checking only whether an `@since` string exists. Resolve uncertain version information from evidence before assigning a tag.

For releases, follow [release-protocol](.agents/skills/release-protocol/SKILL.md).
