var EditableTable = function () {

    return {

        //main function to initiate the module
        init: function () {
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }

                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                jqTds[0].innerHTML = aData[0];
                jqTds[1].innerHTML = '<input type="text" style="width:100%" id="tier_amount" name="tier_amount" class="form-control small" value="' + aData[1] + '">';
                jqTds[2].innerHTML = '<input type="text" style="width:100%" id="tier_range" name="tier_range" class="form-control small" value="' + aData[2] + '">';
                jqTds[3].innerHTML = aData[3];
                if($(aData[4]).attr('id') == null){
                	jqTds[4].innerHTML = '<a class="edit" href="">Save</a>';
                }
                else{
                	jqTds[4].innerHTML = '<a id='+ $(aData[4]).attr('id') +' class="edit" href="">Save</a>';	
                }
                if($(aData[5]).attr('id') == null){
                	if(aData[5].toString().indexOf('data-mode') > -1){
                		jqTds[5].innerHTML = aData[5];
                		/*'<a class="cancel" data-mode="new" href="">Cancel</a>'*/
                	}
                	else{
                		jqTds[5].innerHTML = '<a class="cancel" href="">Cancel</a>';	
                	}
                }
                else{
                	jqTds[5].innerHTML = '<a id='+ $(aData[5]).attr('id') +' class="cancel" href="">Cancel</a>';	
                }
            }

            function validateRange(nRow){
           	 var jqTds = $('>td', nRow);
           	 var jqInputs = $('input', nRow);
           	 var beginRange = removeCommaFromAmount(jqInputs[1].value.trim());
           	 var endRange = removeCommaFromAmount(jqTds[3].innerText);
           	 if(parseInt(beginRange) > parseInt(endRange)){
           		 return false;
           	 }
           	 var tierId = parseInt(jqTds[0].innerText);
             if(tierId > 1){
             	var aData = oTable.fnGetData(parseInt(tierId)-2);
             	var beginRangePre = parseInt(removeCommaFromAmount(aData[2]));
             	if( beginRangePre >= parseInt(beginRange)){
             		return false;
             	}
             }
           	 return true;
           }
            
            function saveRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                if(jqInputs.length == 0){
                	/**There is no record to edit.
                	 * Returning true, so that nEditable value to null.*/
                	return true;
                }
                if((jqInputs[0].value.trim()=="")||(jqInputs[1].value.trim()=="")){
                	//editRow(oTable, nRow);
					$("#tier_amount").valid();
					$("#tier_range").valid();
                	$("#errorMessageModal").html("Tier Section cannot have empty values");
					$('#myModal5').modal('toggle');
                	return false;
                }
                if( !$("#tier_amount").valid() || !$("#tier_range").valid()){
                	return false;
                }
                if(!validateRange(nRow)){
                	
                	oTable.fnUpdate('<input type="text" style="width:100%" class="form-control small error" value="' + jqInputs[1].value + '">', nRow, 2, false);
                	$("#errorMessageModal").html("Invalid value for begin range.");
					$('#myModal5').modal('toggle');
                	return false;
                }
                
                var aData = oTable.fnGetData(nRow);
                var tierId = parseInt(aData[0]);
                
                /*oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);*/
                oTable.fnUpdate(jqInputs[0].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 2, false);

                
                var jqAnchor = $('a', nRow);
                if($(jqAnchor[0]).attr('id') == null){
                	oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
                }
                else{
                	oTable.fnUpdate('<a id='+ $(jqAnchor[0]).attr('id') +' class="edit" href="">Edit</a>', nRow, 4, false);	
                }
                if($(jqAnchor[1]).attr('id') == null){
                	oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 5, false);
                }
                else{
                	oTable.fnUpdate('<a id='+ $(jqAnchor[1]).attr('id') +' class="delete" href="">Delete</a>', nRow, 5, false);	
                }
                
                if(tierId > 1){
                	var nRowTemp = oTable.fnGetNodes(parseInt(tierId)-2);
                	var endRange = parseInt(removeCommaFromAmount(aData[2])) - 1;
                	oTable.fnUpdate(convertNumberFormat(endRange), nRowTemp, 3, false);
                }
                
                oTable.fnDraw();
                
                /*if(!$("#actual_marker").valid()){
					$("#errorMessageModal").html("Please rectify the highlighted errors !!!");
					$('#myModal5').modal('toggle');
				}*/
                
                return true;
            }

            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                /**Check if either amount or begin range is entered or not. If Any one of the value is entered,
                 * then throw error message.
                 * 
                 * If no values is added by user. Delete that row.*/
                if((jqInputs[0].value.trim()=="") && (jqInputs[1].value.trim()=="")){
                	oTable.fnDeleteRow(nRow);
                }
                else if ((jqInputs[0].value.trim()=="")||(jqInputs[1].value.trim()=="")) {
					$("#tier_amount").valid();
					$("#tier_range").valid();
                	$("#errorMessageModal").html("Tier Section cannot have empty values");
					$('#myModal5').modal('toggle');
                	return false;
                }
                else{
                	oTable.fnUpdate(jqInputs[0].value, nRow, 1, false);
                    oTable.fnUpdate(jqInputs[1].value, nRow, 2, false);
                    /*oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);*/
                    //oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                    oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
                    oTable.fnDraw();	
                }
                
            }

            var oTable = $('#editable-sample,#tier-schedule').dataTable({
                "aLengthMenu": [
                    [5, 10, 20, -1],
                    [5, 10, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 50,
                "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "bAutoWidth": false , 
                "aoColumnDefs": [{
                        'bSortable': true,
                        'sClass' : 'read_only',
                        'aTargets': [0]
                    },
                    {
                    	'sClass' : 'text-right',
                    	'bSortable': false,
                    	'fnRender' : function(oObj){
                    		var convertedAmount = oObj.aData[1];
                    		if(convertedAmount != null && convertedAmount != 'undefined' && $('input:radio[name=optionsRadiosDenominationTier]:checked').val() == '$' ){
                    			return convertAmountFormat(convertedAmount.toString());	
                    		}
                    		else if(convertedAmount != null && convertedAmount != 'undefined' && $('input:radio[name=optionsRadiosDenominationTier]:checked').val() == '%'){
                    			return convertPercentageFormat(convertedAmount.toString());
                    		}
                    		return convertedAmount;
                    	},
                    	'aTargets' : [1]                    	
                    },
                    {
                    	'sClass' : 'text-right',
                    	'bSortable': false,
                        'aTargets': [2]
                    },
                    {
                    	'sClass' : 'text-right read_only',
                    	'bSortable': false,
                        'aTargets': [3]
                    },
                    {
                        'bSortable': false,
                        'aTargets': [4]
                    },
                    {
                        'bSortable': false,
                        'aTargets': [5]
                    },
                ],
            });

            jQuery('#tier-schedule_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
            jQuery('#tier-schedule_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

            var nEditing = null;

            $('#tier-schedule_new').click(function (e) {
                e.preventDefault();
                
                
                var maxTierId = 1;
                var maxRange = convertNumberFormat(99999999);
                
                var noRecord = true;
                
                $('#tier-schedule tbody').find('tr').each(function(i) {
                	var tds = $(this).find('td'),
                	tierId = tds.eq(0).text();
                	
                	if(tierId == 'No data available in table'){
                		nEditing = null;
                	}
                });	

                
				var valid = true;
				if(nEditing != null){
					valid = saveRow(oTable, nEditing);
					if(valid){
						nEditing = null;
					}
				}
				
				if(valid){
	                $('#tier-schedule tbody').find('tr').each(function(i) {
	                	var tds = $(this).find('td'),
	                	tierId = tds.eq(0).text();
	                	range = tds.eq(2).text();
	                	
	                	if(tierId != 'No data available in table' && nEditing != null){
	                		noRecord = false;
	                	}
	                   	if(tierId >= maxTierId){
	                   		maxTierId = parseInt(tierId) + 1;
	                   	}
	                });	

					if(noRecord || ( $("#tier_amount").valid() && $("#tier_range").valid() ) ){
						var aiNew = oTable.fnAddData([maxTierId, '', '', maxRange,
	                        '<a class="edit" href="">Edit</a>', '<a class="cancel" data-mode="new" href="">Cancel</a>'
		                ]);
		                var nRow = oTable.fnGetNodes(aiNew[0]);
		                editRow(oTable, nRow);
		                
		                if(maxTierId > 1){
		                	var nRowTemp = oTable.fnGetNodes(parseInt(maxTierId)-2);
		                	oTable.fnUpdate('', nRowTemp, 3, false);
		                }
		                
		                nEditing = nRow;						
					}
					else{
						$("#tier_amount").valid();
						$("#tier_range").valid();
						$("#errorMessageModal").html("Please rectify the highlighted errors !!!");
						$('#myModal5').modal('toggle');
					}					
				}

            });

            $('#editable-sample a.delete,#tier-schedule a.delete').live('click', function (e) {
                e.preventDefault();
                var id = $(this).attr('id');
                if (confirm("Are you sure you want to delete this row ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                oTable.fnDeleteRow(nRow);
                
                $.ajax({
                url: '/SIM/program/v1/removeTier/'+id,
                type: "GET",
                dataType: 'json',
                success: function(data) {
                }
             	 });
                //alert("Deleted! Do not forget to do some ajax to sync with backend :)");
            });

            $('#editable-sample a.cancel,#tier-schedule a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                    if(nRow == nEditing){
                    	nEditing = null;
                    }
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $('#editable-sample a.edit,#tier-schedule a.edit').live('click', function (e) {
                e.preventDefault();
                var tierAmountType =  $('input:radio[name=optionsRadiosDenominationTier]:checked').val();
                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];
                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                	var valid=saveRow(oTable, nEditing);
                    /*restoreRow(oTable, nEditing);*/
                	if(valid){
                        editRow(oTable, nRow);
                        nEditing = nRow;	
                	}
                } else if (nEditing == nRow && this.innerHTML == "Save") {
                    /* Editing this row and want to save it */
                    var valid=saveRow(oTable, nEditing);
                    if(valid){
                    	nEditing = null;
                    }
/*                    $("#informationMessageModal").html("Please click on 'Save Changes' button to permanently  update these changes in database !!!");
                    $('#myModal6').modal('toggle');*/
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
                
            	if(tierAmountType == '%'){
            		$("#optionsRadiosPercentageTier1").prop('checked', true);
            	}
            	else{
            		$("#optionsRadiosDollarSignTier1").prop('checked', true);
            	}
            });
        }

    };

}();