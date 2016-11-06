<!-- @@author A0153723J -->
# Test Scipt

## Loading Sample Data

1. Launch SuperTasker.jar.
2. An auto-generated data file `taskbook.xml` is in the folder `data` where SuperTasker.jar is located.
3. Replace the original `taskbook.xml` with `SampleData.xml` renamed as `taskbook.xml`.

## Feature Testing (Note that we are assuming you will be testing these commands succesively)

#### Help

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| help | Loads a browser window to the user guide."Opened Help Window" appears in result display. |
| hlep |  "Unknown Command" appears in result display. |           

#### Adding a task (After adding the first task, the list of pending tasks is displayed.)

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| add "follow up with Jack on sales report" | Adds a floating task named follow up with Jack on sales report. |
| add "assignment 3" October 12th | Adds a deadline named assignment 3 due on 12 Oct 2016. |
| add "assignment 3" Oct 12th at 2pm | Adds a deadline named assignment 3 due on 12 Oct 2016, 2pm. |
| add "project Highlight" Oct 1st to Nov 14th | Adds an event named project Highlight starting on 1 Oct 2016 to 14 Nov 2016. |
| add "meeting with John" Oct 1st at 1pm to 2pm | Adds an event named meeting with John on 1 Oct 2016, 1pm to 2pm. |
| add "maths test" Oct 12th at 3 O'clock | Adds a deadline named maths test on Oct 12th at 3am |
| add "high school reunion" Sept | Adds a deadline named high school reunion on Sept 1st at the current time. |
| add check todays weather | "Invalid command format" appears in result display. |

#### Completing a task (Click on list all if not all tasks are displayed)

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| complete 1 | Completed task: jump around due 11 Apr 2016 17:30 appears in result display. This task is marked as completed and moved to the completed list. |
| complete 25 | Completed task: buy groceries appears in result display. This task is marked as completed and moved to the completed list. |
| complete retire happily | "Invalid command format" appears in result display. |
| complete 100 | "The task index provided in invalid" appears in result display. |

#### Listing tasks

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| list /c | All completed tasks are completed |
| list /p | All pending tasks are listed |
| list /a | All tasks are listed |
| list all | "Invalid command format!" appears in the result diplay |
| list pending | "Invalid command format!" appears in the result diplay |
| list completed | "Invalid command format!" appears in the result diplay |
| list |  "Listed tasks due today" appears in the result diplay |
| list Sept 3 |  "Listed tasks on 3 Sep 2016" appears in the result diplay |
| list Nov 7 |  "Listed tasks on 7 Nov 2016" appears in the result diplay |

> Type "list /a" to show all tasks or click on the "List All" button

#### Deleting tasks

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| delete 1 | "Deleted task: jump around due 11 Apr 2016 17:30" appears in the result display and is deleted from the taskbook |
| delete 31 | "Deleted task: error 404" appears in the result display and is deleted from the taskbook |
| delete 100 | "The task index provided in invalid" appears in result display. |
| delete "have lunch" |  "Invalid command format!" appears in the result diplay |

#### Find

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| find have | "have lunch" and "have dinner" are listed |
| find do have | tasks with either "have" or "do" are listed |
| find WhatsApp | "0 tasks listed!" appears in result diplay |

<!-- @@author A0153658W -->
#### Store

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| store | Launches a window file explorer to specify save location |
| store C:\Users\Jim\Desktop | Specifies save location to Jim's Desktop folder |
| store C://Users\Jim/Desktop | "Invalid command format!" appears in result diplay |

> Type "list /p" to show pending tasks or click on the "List Pending" button

#### Edit

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| edit 1 "have dinner" | Edits task at index 1 from deadline task to floating task |
| edit 2 "afternoon tea" from November 11 2pm to November 13 3pm | Edits task at index 2 from deadline task to floating task starting on 11 Nov 2016 2pm to 13 Nov 2016 3pm. |
| edit 1 November 10 7pm | Edits task at index 1 from floating task to a deadline task due 10 Nov 2016 at 7pm |
| edit 27 Oct 21st at 1pm to 8pm | Edits task at index 27 from floating task to an event task starting on 21 Oct 2016 1pm to 8pm |
| edit 27 "study break" | Edits task at index 27 from event task to floating task |
| edit 23 November 10, 2020 7pm | Edits task at index 23 from event task to a deadline task due 10 Nov 2020 at 7pm |

> Type "list /c" to show completed tasks or click on the "List Complete" button

#### Clear

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| clear | "Completed tasks have been cleared!" appears in result display |
| clear | "There are no completed tasks to be cleared!" appears in result display |
| claer | "Unknown command" appears in result diplay |
| clear /a | "Task book has been cleared!" appears in result diplay |

> Type "list /a" to show all tasks to verify all tasks are cleared 

#### Undo

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| undo | "Undo successful, reversed action: clear /a" appears in result display (note that this brings back "list pending" tasks by default.) |
| unod | "Unknown command" appears in result diplay |
| add "undo test" | Adds a floating task named undo test. |
| undo | "Undo successful, reversed action: add undo test" appears in result display |
| add "undo test" Sept 10pm | Adds a deadline task named undo test due 1 Sept 10pm |
| undo | "Undo successful, reversed action: add undo test 01-09-2016 22:00" appears in result display |
| add "undo test" Sept 10pm to 11pm | Adds an event task named undo test starting 1 Sept 10pm to 11pm. |
| undo | "Undo successful, reversed action: add undo test 06-11-2016 22:00 06-11-2016 23:00" appears in result display |
| delete 1 | "Deleted task: have dinner due 10-11-2016 19:00" appears in the result display and is deleted from the taskbook |
| undo | "Undo successful, reversed action: delete 1" appears in result display |
| edit 1 "undo test" | Edits task at index 1 from deadline task to floating task. |
| undo | "Undo successful, reversed action: edit 2 undo test" appears in result display |
| edit 1 Sept 10 7pm | Edits task at index 1 from floating task to deadline task due 10 Sept 2016 7pm. |
| undo | "Undo successful, reversed action: edit 1 10-09-2016 19:00" appears in result display |
| edit 1 Sept 10 7pm to 8pm| Edits task at index 1 from floating task to deadline task starting 10 Sept 2016 7pm to 8pm. |
| undo | "Undo successful, reversed action: edit 1 10-09-2016 19:00 10-09-2016 20:00" appears in result display |

> Type "list /p" to show pending tasks or click on the "List Pending" button

#### Sort

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| sort | sorts tasks from earliest date to latest dates, sorted on End Date value |
| srot | "Unknown command" appears in result diplay |

#### Select

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| select 50 | "Selected Task: 50" appears in result display and jumps to 50th task |
| select 100 | "The task index provided is invalid" appears in result diplay |
