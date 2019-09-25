 This is a simple Facility Display app.
 This sample project uses clean architecture so it may seem over-complicated,
 However this architecture allow us to maintain large code base in a layered manner.
 
 
 ## Architecture
 Project has multiple modules named mobile-ui, presentation,domain,data,remote which represents different
 layer of clean architecture as described below. 
 
 # UI Layer
 Contains Android code such as Activity Adapter etc under mobile-ui module
 
 # Presentation Layer
 Contains ViewModel and view model mapper
 
 # Domain Layer
 Contains Domain/Business logic as per requirements
 Each requirement is represented by UseCase which is implemented as Interactors
 
 
 # Data Layer
 Work as a selection layer to choose the dataSource or repositories(i.e remote or database)
 
 # Database Layer
  Handles DB operations and handover db model to data layer with the help of mapper 
  
 # Remote/Network Layer
 Handles APIs, network call and handover remote model to data layer with the help of mapper 