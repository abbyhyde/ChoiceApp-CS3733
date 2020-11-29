// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://djffapxaf4.execute-api.us-east-2.amazonaws.com/Alpha/";  

var add_url        = base_url + "participateChoice";   // POST
var list_url       = base_url + "list";    // GET
var new_url        = base_url + "createChoice";
var approve_url    = base_url + "approve";
var disapprove_url = base_url + "disapprove";
var unselect_url   = base_url + "unselect";
//need to add more here?

