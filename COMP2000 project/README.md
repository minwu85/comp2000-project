## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


## How to start simulated

1. run the code
2. press space to start simulated


## Goal to be done

1. topPanel
- add a side bar/panel display on top of screen, this side bar/panel will display the time, 
- add and finish pause and continue buttons
        
2. left panel
- then add a left side bar/panel display on the left of the screen
- listing the number of passengers and the number of passengers on the train 
- eg: g.drawString("Passengers: " + passengers.size(), 40, 190);
- g.drawString("On Train: " + train1.getPassengers().size(), 40, 210);

3. table of train line - need to wait until the station name to be done
- set a point for the train 
- eg Start point for t1, and when it reach to the end
- eg link with the left panel display the time when the train is at a certain station

4. Time issues 
- which need to link with the train time table after 3 being done
- Fix time display to show the time in the format of HH:MM

5. accident issues
- for later accident time later
- eg T1 accident at Station 3, result a stop of the train for 5 minutes, and then continue to move after 5 minutes
- or a replaceable bus or other solution for the passage
        
**different type of accident for train delay**
(feature could be add later on)
- fire
- rain
- earthquake
- someone died
- train breakdown
- train collision
- train derailment
         
### List to be later improve/add

1. looks of 
- passager (probably change another way to display)
- train (image?)
2. bus
3. Weather which link to accident section