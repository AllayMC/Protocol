# Upstream synchronization

Source: [CloudburstMC/Protocol, branch 3.0](https://github.com/CloudburstMC/Protocol/tree/3.0).

The 2026-10-13 synchronization starts after `98d308d5` (v2169, already present in local commit `33e4e4bf`) and ends at `6f466f0c88b0ee38b561835a324cba3817ead4f6`.

Each of the 26 upstream non-merge commits has a separate local commit with its original author, author date, subject, and a `cherry picked from commit` SHA. Two commits are empty tracking records because their changes are already covered or target the removed Adventure module. The merge commit `4128f334` adds no changes beyond `5ba43c4d`.

## Commits

| Upstream commit | Subject | Fork adaptation |
| --- | --- | --- |
| [db36d289](https://github.com/CloudburstMC/Protocol/commit/db36d289968a1d07d6591f0d54426cc058b227c0) | Bump NBT | Updated NBT; kept the dependency catalog used by AllayMC. |
| [b01ef8ab](https://github.com/CloudburstMC/Protocol/commit/b01ef8abdade3b415e67ffb86892749b74c5d016) | 26.50 | Adapted v2192 to records, raw strings, PrimitiveShapes, nested diagnostics, and bedrock.util. Kept existing constructors and protected inventory hooks. Upstream VERSIONS entries are listed in README. The annotation on upstream-only PERSONA_ANIMATIONS has no matching fork symbol. |
| [6d903898](https://github.com/CloudburstMC/Protocol/commit/6d9038984c0ccd22b0beac347cf50543f81a9ff3) | Fix ItemStackResponseSlot filtered name | Applied, with fork import and accessor paths where needed. |
| [5ee1d85b](https://github.com/CloudburstMC/Protocol/commit/5ee1d85b36ecf702b2017dd7af688ee9785c3da4) | Fix ItemUseInventoryTransaction hand field type (#355) | Applied, with fork import and accessor paths where needed. |
| [03d84626](https://github.com/CloudburstMC/Protocol/commit/03d84626de7a22af2a48c61d1eebfd37526db0fa) | Add party blue color §w | Empty tracking commit: Adventure is absent from the fork; raw strings already preserve section-sign colors. |
| [0699e719](https://github.com/CloudburstMC/Protocol/commit/0699e7194e72da5ccd69dccb162de9b11ecb3e4a) | Fix ClientboundUpdateSoundDataPacket v2168 (#356) | Used record accessors for sound data. |
| [9df9864c](https://github.com/CloudburstMC/Protocol/commit/9df9864c2bf79197a30ddff72dc0008dca93a976) | Fix PlayerUpdateEntityOverrides and PresenceConfiguration v2168 (#358) | Used PresenceConfiguration record accessors and retained skin color parsing. |
| [863e6e91](https://github.com/CloudburstMC/Protocol/commit/863e6e91dad6f4e8316934b271fc8ec9bd821fe5) | SetPlayerFurnaceOptionsPacket can be sent by both server and client | Applied, with fork import and accessor paths where needed. |
| [f222c34b](https://github.com/CloudburstMC/Protocol/commit/f222c34bb27ce7fb8ce0e7c60fa843c27733ff6f) | Update v2192 to v2193 release | Renamed adapted codecs, including PrimitiveShapes, to v2193. |
| [1e853dbc](https://github.com/CloudburstMC/Protocol/commit/1e853dbc564e3f7ae20ae847e4448abb905a00e8) | Update DimensionDataSerializer_v2193.java | Applied, with fork import and accessor paths where needed. |
| [97eae1a3](https://github.com/CloudburstMC/Protocol/commit/97eae1a3a89bcfa6d0a8958f02f1808ea1bcb807) | Fix DimensionDefinition height range for v2193 (#360) | Applied, with fork import and accessor paths where needed. |
| [7b029966](https://github.com/CloudburstMC/Protocol/commit/7b029966332beca51ba5491fd6cbf3ad0aa2fc51) | A few fixes | Kept PresenceConfiguration as a record and used the fork item helper API. |
| [18bf1250](https://github.com/CloudburstMC/Protocol/commit/18bf12501c0e33e9969957873b9021438c0886e5) | Only call getBlockDefinition once | Applied to existing item writers; v975 inherits its writer in this fork. |
| [b15b7e06](https://github.com/CloudburstMC/Protocol/commit/b15b7e0694d6330916c4157d5093ddc7727e8130) | Add getter for SoundEvent typeMap for easier multi version filtering | Applied, with fork import and accessor paths where needed. |
| [2bd3ee0b](https://github.com/CloudburstMC/Protocol/commit/2bd3ee0baca0f9519bbe230724853f5c43b2f975) | Fix area effect cloud metadata deprecation | Applied, with fork import and accessor paths where needed. |
| [989332f6](https://github.com/CloudburstMC/Protocol/commit/989332f6d4c2a93cf41212c9ba1b5dd14b00f693) | Fix event payload field widths since v898 | Adapted event record accessors, test imports, and MobKilledEventData constructor order. |
| [533548ce](https://github.com/CloudburstMC/Protocol/commit/533548ce12d4a8176a14d07b30e381096319df8d) | Read the movement prediction sync entity id as a signed unique id (#361) | Kept the fork serializer hooks and provided deprecated runtime-ID accessors forwarding to the corrected unique-ID field. |
| [b884d834](https://github.com/CloudburstMC/Protocol/commit/b884d83439e6e2c69f4231277ee2bbd532006d0c) | Use signed VarInt for sound loop count | Applied, with fork import and accessor paths where needed. |
| [29bd1453](https://github.com/CloudburstMC/Protocol/commit/29bd14538d6095506c672322678b340fe879e3a1) | Reduce memory allocations on the write path (#357) | Moved VarInts tests into bedrock-codec. Kept AllayMC coordinates and release version. |
| [5ba43c4d](https://github.com/CloudburstMC/Protocol/commit/5ba43c4d764e77c8b43955ad37995f1548a6ec8b) | Use signed longs instead of unsigned longs | Empty tracking commit: PrimitiveShapes already uses signed attached-entity IDs. Also covers merge 4128f334, whose tree matches this commit. |
| [23393800](https://github.com/CloudburstMC/Protocol/commit/23393800dac2aa00be2ab588eccf6ad157e73e04) | Start work on 26.60 | Used PassengerOfBlockArguments records and immutable skin builders. Kept WorldType in its existing package and retained NetEase slot types. |
| [30ddc84c](https://github.com/CloudburstMC/Protocol/commit/30ddc84cfdfcb3832bce7db112876e0ccb9e49da) | New 26.60 packets | Used records for new action and nested packet data. Extended existing attribute and sound records, and kept fork packet APIs. |
| [3f032ea9](https://github.com/CloudburstMC/Protocol/commit/3f032ea915142de46a3020146f7abb373eaa4716) | Bump netty-transport-raknet to 1.1.0.CR2-SNAPSHOT | Pinned RakNet to 1.1.0.CR2-20261009.154359-1, the concrete build of the requested upstream snapshot. |
| [f638d309](https://github.com/CloudburstMC/Protocol/commit/f638d309b5f60f21a3294d03a77a2583532bb7b3) | Split flushes into batches the transport can carry | Applied, with fork import and accessor paths where needed. |
| [4b333e31](https://github.com/CloudburstMC/Protocol/commit/4b333e319f8efd9ddb2203d07c88412afeb49ffb) | Share input flag reading between the PlayerAuthInput serializers | Used bedrock.util.Preconditions. |
| [6f466f0c](https://github.com/CloudburstMC/Protocol/commit/6f466f0c88b0ee38b561835a324cba3817ead4f6) | Bump nbt to 3.1.0.Final | Updated NBT to 3.1.0.Final within the fork dependency catalog. |

## Validation

`./gradlew build` covers both modules. Imported upstream tests cover event field widths, signed movement IDs, VarInts, packet headers, and transport batch limits. `UpstreamSyncSerializationTest` adds 12 tests for record adaptations, dimensions, camera presets, primitive text, diagnostics, furnace options, skin PlayFab IDs, passenger data, sound variants, attribute layers, nested packet data, and input flag limits.

A separate local fix corrects the v2225 fade decoder: the wire stores duration before target volume, while `FadeSoundData` takes target volume before duration.

v2225 includes the upstream 26.60 development changes available at the pinned commit. Validation uses unit tests and local builds; live client/server interoperability has not been tested.

## Finding a port

Run `git log --all --fixed-strings --grep=<full-upstream-SHA>` to find the corresponding local commit. The fork continues to use its own release version and Maven coordinates.
