# Test Scipt

## Loading Sample Data

0. Open SuperTasker.jar file.
1. Take note of the file directory which contains taskbook.xml in the status bar.
2. Replace this with the sample data, making sure to rename this file as taskbook.xml.

## Feature Testing (Note that we are assuming you will be testing these commands succesively)

#### help menu

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| help | Loads a browser window to the user guide."Opened Help Window" appears in result display. |
| hlep |  "Invalid Command" appears in result display. |           

#### adding a task

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| add "follow up with Jack on sales report" | Adds a floating task named follow up with Jack on sales report. |
| add "assignment 3" October 12th | Adds a deadline named assignment 3 due on 12 Oct 2016. |
| add "assignment 3" Oct 12th at 2pm | Adds a deadline named assignment 3 due on 12 Oct 2016, 2pm. |
| add "project Highlight" Oct 1st to Nov 14th | Adds an event named project Highlight starting on 1 Oct 2016 to 14 Nov 2016. |
| add "meeting with John" October 1st at 2pm to Oct 1st 16:00 | Adds an event named meeting with John on 1 Oct 2016, 2pm to 4pm. |
| add "maths test" Oct 12th at 3 O'clock |Adds a deadline named maths test on Oct 12th at 3am|
| add "high school reunion" Sept | Adds a deadline named high school reunion on Sept 1st at the current time. |
| add check todays weather | "Invalid command format" appears in result display. |
| add "assignment 3" 16/16/16 |  "Unknown Command" appears in result display. |
| add "meeting with John" October 1 2pm to December 1st 16:00 | "Unknown Command" appears in result display. | 

#### completing a task (press list all if not all tasks are display)

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| complete 1 | Completed task: jump around due 11-04-2016 17:30 appears in result display. This task is marked as completed and moved to the completed list. |
| complete 25 | Completed task: buy groceries appears in result display. This task is marked as completed and moved to the completed list. |
| complete retire happily | "Invalid command format" appears in result display. |
| complete 100 | "The task index provided in invalid" appears in result display.|
| complete 1| "The task is already complete!" appears in result display. "

#### listing tasks

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| list /c | All completed tasks are completed |
| list /p | All pending tasks are listed |
| list /a | All tasks are listed |
| list all| "Invalid command format!" appears in the result diplay|
| list pending| "Invalid command format!" appears in the result diplay|
| list completed | "Invalid command format!" appears in the result diplay|
| list today |  "Invalid command format!" appears in the result diplay |

#### Deleting tasks

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| delete 1 | "Deleted task: jump around due 11-04-2016 17:30" appears in the result display and is deleted from the taskbook |
| delete 31 | "Deleted task: make movie from holiday photos" appears in the result display and is deleted from the taskbook |
| delete 100 | "The task index provided in invalid" appears in result display. |
| delete "have lunch" |  "Invalid command format!" appears in the result diplay |

#### Find

| Command | Expected Result |
|----------------------------------------------------|-----------------------------------------------------------------------|
| find have | "have lunch" and "have dinner" are listed|
| find do have | tasks with either "have" or "do" are listed|
| find WhatsApp| "0 tasks listed!" appears in result diplay|

