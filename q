Changing AuthManager methods


Old commit chatRoomName & commit message have been corrected. Credentials fields
are defined using constructor. Singleton (single thread) is used for
creating and receiving AuthenticationManager entity. Methods in the
AuthManager interface have been changed. Their implementation has also
been changed.

# Please enter the commit message for your changes. Lines starting
# with '#' will be ignored, and an empty message aborts the commit.
#
# Date:      Tue Aug 7 18:14:25 2018 +0300
#
# On branch master
# Your branch is ahead of 'origin/master' by 1 commit.
#   (use "git push" to publish your local commits)
#
# Changes to be committed:
#	modified:   app/src/main/java/com/bigsur/AndroidChatWithMaps/AuthManager/AuthManager.java
#	modified:   app/src/main/java/com/bigsur/AndroidChatWithMaps/AuthManager/AuthenticationManager.java
#	modified:   app/src/main/java/com/bigsur/AndroidChatWithMaps/AuthManager/Credentials.java
#	modified:   app/src/main/java/com/bigsur/AndroidChatWithMaps/LoginActivity.java
#	modified:   app/src/main/java/com/bigsur/AndroidChatWithMaps/StartActivity.java
#	new file:   q
#
