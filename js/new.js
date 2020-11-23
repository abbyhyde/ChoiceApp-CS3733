/**
 * Respond to server JSON object.
 *
 */
function processNewResponse(desc, alt1, alt2, alt3, alt4, alt5, numTeam, response) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("Choice ID:" + response);
  var js = JSON.parse(response);

  var choiceId = js["choiceId"];
  var status = js["statusCode"];
  
  if (status == 200) {
    // Update computation result
    document.newForm.choiceId.value = choiceId
  } else {
    var msg = js["error"];
    document.newForm.choiceId.value = "error:" + msg
  }
}

function handleNewClick(e) {
  var form = document.newForm;
  var desc = form.desc.value;
  var alt1 = form.alt1.value;
  var alt2 = form.alt2.value;
  var alt3 = form.alt3.value;
  var alt4 = form.alt4.value;
  var alt5 = form.alt5.value;
  var numTeam = form.numTeam.value;

  var data = {};
  data["desc"] = desc;
  data["alt1"] = alt1;
  data["alt2"] = alt2;
  data["alt3"] = alt3;
  data["alt4"] = alt4;
  data["alt5"] = alt5;
  data["numTeam"] = numTeam;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", new_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    console.log(xhr);
    console.log(xhr.request);
    
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processNewResponse(desc, alt1, alt2, alt3, alt4, alt5, numTeam, xhr.responseText);
    } else {
      processNewResponse(desc, alt1, alt2, alt3, alt4, alt5, numTeam, "N/A");
    }
  };
}