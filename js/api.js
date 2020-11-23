// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://djffapxaf4.execute-api.us-east-2.amazonaws.com/Alpha";  

var add_url    = base_url + "add";   // POST
var list_url   = base_url + "list";    // GET
var create_url = base_url + "create";    // POST
var delete_url = base_url + "delete";    // POST with {name} so we avoid CORS issues
var new_url    = base_url + "new";
var choice_url = base_url + "choice";
//need to add more here?

