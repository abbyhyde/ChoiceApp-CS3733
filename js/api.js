// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://djffapxaf4.execute-api.us-east-2.amazonaws.com/Gamma/";  

var add_url        = base_url + "participateChoice";   // POST
var list_url       = base_url + "getChoices";    // GET
var new_url        = base_url + "createChoice";
var approve_url    = base_url + "selectApproval";
var disapprove_url = base_url + "selectDisapproval";
var unselect_url   = base_url + "unselectOpinion";
var feedback_url   = base_url + "addFeedback";
var delete_url     = base_url + "deleteChoices";
var complete_url     = base_url + "completeChoice";
//need to add more here?

