# CPSC 356 Final Project: Character Picker
By Matthew Shiroma

## Summary
- This app will display a list of Characters, fictional and nonfictional beings in a linear fashion. Each Character has their own attributes (i.e. name, age, description). You can view each character in more detail by selecting them from the list. On the detailed view screen, you can rate the characters as well on a 5 star scale. Each character will keep track of the average rating they receive as well. You can delete Characters, add new Characters, update Characters, and move Characters’ position on the list. The app will also have data persistence, meaning whenever you launch the app, the previous data will be read back into the app.

## Features
- All Characters have their own name, age, sex, description, profile picture, and ratings.
- A RecycleView that dynamically displays all of the characters that the user created.
  - When opening the app for the first time, a preset amount of characters will be auto generated.
  - Each cell in the RecycleView displays the profile picture, name and average rating of a character.
  - Can add new characters from this screen.
    - Auto generates fields for the new character.
    - Can also discard the new character immediatly.
          - When discarding, an alert pops up, warning the user about their action. If they choose to do so, the new character is removed from the app.
  - Can also reorder and delete characters on this screen, using swipes and drag and drop.
- From the RecycleView, clicking on a character cell brings up a detailed view of that character's attributes.
  - Can rate the currently selected character on a scale of 1-5, which dynamically changes the value once a rating has been made (can only do one rating while viewing the character)
  - Can swipe left and right on this screen to view all of the other characters in a detailed view.
- On the menu bar, can select to edit or delete the selected character.
  - Hitting select brings up another screen that allows the user to change all of the character's attributes (except for ratings)
    - Touching the photo allows the user to change the profile picture of the character.
  - Can either save the changes they made, or discard them by going back on either the Up button or the Back button.
    - When discarding, an alert pops up, warning the user about their action. If they choose to do so, their changes are unsaved.
- All changes are saved to a SQLite database, meaning when the user opens the app again, their last changes will be present in the app.    

## Issues
- After making changes and either saving/discarding them on a character, the current view of the character does not reflect those changes. It only gets updated once the user navigates back to the RecyclerView.

## References
- [Picky Landlord](https://github.com/ChapmanCPSC356Fall2017/picky-landlord)
- [My Item Rankings Assignment](https://github.com/ChapmanCPSC356Fall2017/item-rankings-shiro105)
- [Adding external libraries in Android Studio](https://stackoverflow.com/questions/25610727/adding-external-library-in-android-studio)
- [Spinner](https://www.mkyong.com/android/android-spinner-drop-down-list-example/)
- [Only allow integers in RatingBar](https://stackoverflow.com/questions/14218029/how-to-allow-full-values-integers-only-in-ratingbars)
- [How to calculate 5 star ratings](https://stackoverflow.com/questions/10196579/algorithm-used-to-calculate-5-star-ratings)
- [Find the id of a drawable in an imageView](https://stackoverflow.com/questions/4526585/get-the-id-of-a-drawable-in-imageview)
- [Getting the value in a spinner](https://stackoverflow.com/questions/10331854/how-to-get-spinner-selected-item-value-to-string)
- [Issue with oncreateoptionsmenu](https://stackoverflow.com/questions/20226897/oncreateoptionsmenu-not-called-in-fragment)
- [Concert a drawable resource into a bitmap](https://stackoverflow.com/questions/8717333/converting-drawable-resource-image-into-bitmap)
- [Pushing a Bitmap into a bundle](https://stackoverflow.com/questions/33797036/how-to-send-the-bitmap-into-bundle)
- [How to solve binder transaction error](https://stackoverflow.com/questions/23407821/how-to-solve-error-failed-binder-transaction-in-android-4-4)
- [Compressing bitmaps](https://stackoverflow.com/questions/8417034/how-to-make-bitmap-compress-without-change-the-bitmap-size)
- [Storing and retriving a bitmap into a database](https://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android)
- [getWriteableDatabase vs getReadableDatabase](https://stackoverflow.com/questions/6597277/getwritabledatabase-vs-getreadabledatabase)
- [How to use SQLite](https://developer.android.com/training/data-storage/sqlite.html)
- [How to check if a table is empty](https://stackoverflow.com/questions/22630307/sqlite-check-if-table-is-empty)
- [Close an activity after an alert dialoge](https://forums.xamarin.com/discussion/15804/close-activity-after-alert-dialog)
- [Using an alert dialoge](https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android)
- [How to handle the native up button](https://stackoverflow.com/questions/8887390/how-to-handle-up-button)
