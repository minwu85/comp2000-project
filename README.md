# comp2000-simulation project

The team name: OOPP The GOOP

The team members: Ashton, Luke, Daniel, Min, Amanda

## Project Goal





## FlowChart 

explain each Class do

Plain-text version:

```
Panel (the window + game loop)
 ├─ SidePanel        draws the top bar (clock/pause) and 4 train cards
 ├─ Time             ticking clock, drives every update
 ├─ Passenger        a commuter moving stop to stop
 └─ Train  ── extends ──▶ Vehicles
                          ├─ Routes   ordered list of Stops for one line
                          │   └─ Stops   a single station (x, y, name)
                          └─ ArrayList<Passenger>  who is on board