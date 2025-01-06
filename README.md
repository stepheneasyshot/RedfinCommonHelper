# RedfinCommonHelper

Pixel 5(redfin)设备上开发的apk，共享的公共库

Add it in your root build.gradle at the end of repositories:
```
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
	dependencies {
	        implementation 'com.github.stepheneasyshot:RedfinCommonHelper:Tag'
	}
```
