This is a simple Java exercise that tries to
meet following requirements:
* Simple server that provides hosting of its screen
  (a static image taken periodically)
* There will be a chatting attached

The resulting server is hosted on port 8080 and
the index web page is accessible from URI /screenHost/.
Example: localhost:8080/screenHost/
The goal of this project is to target the bare minimum
needed to fulfilling the required functionality.
Following is a list of missing/untested features:
* Handle unicode char set in chat
* Register user/limit content - register user and limit access.
  (Now a chat name is not checked even for uniqueness)
* Improve performance
* Introduce dynamic configuration, or configuration via config file
  (currently lot of parameters are hardcoded)
* ...
