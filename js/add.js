/**
 * Respond to server JSON object.
 *
 */
function processAddResponse(choiceID, name, pass) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + result);
  var js = JSON.parse(result);

  var computation = js["result"];
  var status      = js["statusCode"];
  
  if (status == 200) {
    // Update computation result
    document.addForm.result.value = computation
  } else {
    var msg = js["error"];
    document.addForm.result.value = "error:" + msg
  }

  refreshChoice();
}

function handleAddClick(e) {
  var form = document.addForm;
  var choiceID = form.choiceID.value;
  var name = form.name.value;
  var pass = form.pass.value;

  var data = {};          //NEED TO CHANGE: Make this for a choice
  data["choiceID"] = choiceID;
  data["name"] = name;
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
      processAddResponse(choiceID, name, pass, xhr.responseText);
    } else {
      processAddResponse(choiceID, name, pass, "N/A");
    }
	document.getElementById("choice").style.visibility="visible";
 

  };
}