# NextTrainIndicator


- Application demo in below link: https://appetize.io/app/cycaa5ba5293vpyv9gf3bdcd5c
- Application source code: https://www.dropbox.com/s/ss1zk3406gmdcy9/NextTrainIndicator.zip?dl=0
- Application APK: https://www.dropbox.com/s/996y3frurfhf053/NextTrainIndicator.apk?dl=0


**Application Description:**

	Application show next train time table with remaining time and Train Destination name.
	This time table refresh every 10s.
	Default time frame for Time table is 15m. User have option to change this time frame. If there is not train available for given Time frame user with given empty msg “No train available for next _min”

![screenshot_1513061273](https://user-images.githubusercontent.com/3032854/34292545-56f54b5c-e727-11e7-8a6d-26f77d7ff57a.png)

**Design pattern: Model View Presenter (MVP) design pattern is been followed**
- Train.java - model
- TrainScheduleView.java, TrainScheduleActivity.java - View
- TrainSchedulePresenter.java - Presenter

**Test Driven Development (TDD) & Unit testing:**
- TrainScheduleTest.java
- TrainSchedulePresenterTest.java 

**Test coverage for business logic class**
- TrainSchedulePresenter.java
- TrainSchedule.java

**UI Automation testing using Espresso**
- TrainScheduleActivityTest.java
- UI Automation test cover below scenario 
- Changing Time frame
- Empty Train time table
- Non empty train time table 
