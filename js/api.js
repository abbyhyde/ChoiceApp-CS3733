// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://djffapxaf4.execute-api.us-east-2.amazonaws.com/Beta/";  

var add_url        = base_url + "participateChoice";   // POST
var list_url       = base_url + "getChoices";    // GET
var new_url        = base_url + "createChoice";
var approve_url    = base_url + "selectApproval";
var disapprove_url = base_url + "selectDisapproval";
var unselect_url   = base_url + "unselectOpinion";
//need to add more here?

