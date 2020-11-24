/**
 * Respond to server JSON object.
 *
 */
function processAddResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + result);
  var js = JSON.parse(result);


  var choiceId = js["choiceId"];
  var description = js["description"];
  //var alternatives
  //var members
  var maxNumMembers = js["maxNumMembers"];
  var isCompleted = js["isCompleted"];
  
  var status = js["statusCode"];
  
  if (status == 200) {
    // Update computation result
    //document.addForm.result.value = computation
    
    //this is where you output all of the values from the variables above
  } else {
    var msg = js["error"];
    //document.addForm.result.value = "error:" + msg;
  }

  //refreshChoice();
}

function handleAddClick(e) {
  var form = document.addForm;
  var choiceId = form.choiceId.value;
  var name = form.teammateName.value;
  var pass = form.password.value;

  var data = {};          //NEED TO CHANGE: Make this for a choice
  data["choiceId"] = choiceId;
  data["memberName"] = name;
  data["pass"] = pass;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", add_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processAddResponse(xhr.responseText);
    } else {
      processAddResponse("N/A");
    }
	document.getElementById("choice").style.visibility="visible";
 

  };
}
