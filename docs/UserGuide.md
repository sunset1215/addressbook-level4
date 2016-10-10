# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `supertasker.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/uiMockUp.jpg" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks due today
   * **`add`**` follow up with Jack on sales report` : 
     adds a task named `follow up with Jack on sales report` to the Task Manager.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * The order of parameters is fixed, with optional parameters being the exception.
> * Parameters `START_DATE` and `END_DATE` has to be in dd-mm-yyyy format (e.g. 23-10-2016).
> * Parameters `START_TIME` and `END_TIME` has to be in 24 hour format (e.g. 1215).
> * Parameter `TIMELEFT` **must be a positive integer**, indicating number of hours left.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task : `add`
Adds a floating task/deadline/event to the task manager.<br>
Format: `add TASK_NAME [-d e/END][-e s/START e/END]` 

> `TASK_NAME` is required for a floating task. For a deadline, `TASK_NAME`   .<br>
  User must specify `END` along with `-d` to set a deadline.<br>
  User must specify `START`, `END` along with `-d` to set an event.

Examples: 
* `add follow up with Jack on sales report -d e/6-10-2016`
* `add project Highlight -e s/1-10-2016 e/14-11-2016`
  
#### Set task as completed : `setcomplete`
Sets an event to the specified task from the task manager.<br>
Format: `setcomplete TASK_INDEX`

> Sets the task at the specified `TASK_INDEX` as complete.<br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
  
Examples: 
* `list`<br>
  `setcomplete 2`<br>
  Set the 2nd task as complete in the task manager.

#### Listing all tasks due today : `list`
Shows a list of all tasks due today in the task manager.<br>
Format: `list`

#### Listing all floating tasks : `listfloat`
Shows a list of all floating tasks in the task manager.<br>
Format: `listfloat`

#### Deleting a task : `delete`
Deletes the specified task from the task manager.<br>
Format: `delete TASK_INDEX`

> Deletes the task at the specified `TASK_INDEX`.<br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find report`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.
  
#### Finding all tasks containing any keyword in their name : `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is not case sensitive. e.g `complete` will match `Complete`
> * The order of the keywords does not matter. e.g. `meeting John` will match `John meeting`
> * Partial words will be matched e.g. `meet` will match `meeting`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `meeting` will match `meeting with John`

Examples: 
* `find Highlight`<br>
  Returns any tasks having names `Highlight` or `highlight`
* `find product highlight showcase`<br>
  Returns any task having names `product`, `highlight`, or `showcase`

#### Finding all tasks within a date range : `finddate`
Finds tasks whose dates fall within the specified range.<br>
Format: `finddate sd/START_DATE ed/END_DATE`

* `finddate 23-10-2016 30-10-2016`<br>
  Returns any task whose dates fall between 23-10-2016 and 30-10-2016

#### Editing a task : `edit`
Edits the specified task from the task manager.<br>
Format: `edit TASK_INDEX [-d e/END][-e [s/START] e/END][-e s/START [e/END]]`

> Edits the task at the specified `TASK_INDEX`.<br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...<br>
  Specified parameters will overwrite previous data.<br>
  User can specify `END` along with `-d` to set a deadline to a task.<br>
  User can specify `START` or/both `END` to set an event to the task. This can also be used to turn a deadline into an event.

Examples: 
* `list`<br>
  `edit 2 ed/23-10-2016`<br>
  Sets a deadline 23rd October 2016 to the 2nd task in the task manager.
  
#### Viewing a task : `view`
Views details of the specified task from the task manager.<br>
Format: `view TASK_INDEX`

> Views details of the task at the specified `TASK_INDEX`.<br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `view 2`<br>
  Displays details of the 2nd task in the task manager.

#### Undoing the last command : `undo`
Undo the last command executed.<br>
Format: `undo`

> Able to undo up to the last 20 commands.<br>
  Only commands that changes data are included (e.g. `add`, `delete`).

#### Clearing completed tasks : `clear`
Clears all completed tasks from the task manager.<br>
Format: `clear` 

#### Specifying data storage location : `store`
Specifies data storage location.<br>
Format: `store [FILE_LOCATION]`

> Stores data of the task manager at the specified `FILE_LOCATION`.<br>
  If `FILE_LOCATION` is not specified, a dialog box will appear and the user can browse for the storage location.

Examples: 
* `store C:\Users\Jim\Desktop\Work`<br>
  Specifies data storage location at 'C:\Users\Jim\Desktop\Work'.
  
* `store`<br>
  Displays a dialog box for user to browse for the storage location.

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ
       
## Command Summary

Command | Format | Description 
----------- | ------------------------------- | :--------- 
Help | `help` | View help on command usage
Add | `add TASK_NAME [-d e/END][-e s/START e/END]` | Add a task
SetComplete | `setcomplete TASK_INDEX` | Set task as complete
List | `list` | List tasks due today
ListFloat | `listfloat` | List all  floating tasks
Delete | `delete TASK_INDEX` | Delete a task
Find | `find KEYWORD [MORE_KEYWORDS]` | Find all tasks containing any keywords
FindDate | `finddate s/START e/END` | Find all tasks within a date range
Edit | `edit TASK_INDEX [-d e/END][-e [s/START] e/END][-e s/START [e/END]]` | Edit a task
View | `view TASK_INDEX` | View details of a task
Undo | `undo` | Undo last command
Clear | `clear` | Clear completed tasks
Store | `store FILE_LOCATION` | Specify storage location
Exit | `exit` | Exit program
