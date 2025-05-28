### ChatManager Github Flow
All commits to `main` branch are published to Modrinth
 * When a commit is pushed, A snapshot build is made which pushes a build versioned with the commit hash to Modrinth
 * If a tag is made, A release build is made which is semver related i.e. 0.0.1

### 3rd Party Pull Requests
**You must not UNDER any circumstance commit to the main branch** All changes should be made via a pull request.

* You should only *version bump* when you are *ready* to release.
* The commit when version bumping should always be separated, and clearly labeled like `v0.0.1 Update`
* All work should be done in a properly labeled branch.
  * If you find you are fixing a bug, label the branch as fix/bug_desc
  * If you find you are adding a feature, label the branch as feat/feat_desc, and so on.