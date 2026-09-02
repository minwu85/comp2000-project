# comp2000-simulation project

The team name: OOPP The GOOP

The team members: Ashton, Luke, Daniel, Minying, Amanda

## Project Goal
Project goal is to have train simulation 

## How the classes link

```mermaid
graph TD
    Frame[java.awt.Frame]:::ext
    Panel -->|extends| Frame
    Panel -->|creates & draws| SidePanel
    Panel -->|creates 4x| Train
    Panel -->|creates| Passenger
    Panel -->|creates| Time

    Train -->|extends| Vehicles
    Vehicles -->|has a| Routes
    Vehicles -->|holds ArrayList of| Passenger
    Routes -->|has a list of| Stops

    Passenger -->|walks along| Routes
    Passenger -->|current / start / end| Stops

    SidePanel -->|reads| Vehicles
    SidePanel -->|reads clock + date| Time
    SidePanel -->|reads names from| Stops

    Time -->|wraps| Timer[javax.swing.Timer]:::ext
    Time -->|formats sim time with| DateTime[java.time.LocalDateTime]:::ext

    classDef ext fill:#eee,stroke:#999,color:#333;
```



## FlowChart 


This explain each Class do


Plain-text version:

```


