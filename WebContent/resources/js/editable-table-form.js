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
                jqTds[0].innerHTML = '<input type="text" class="form-control small" value="' + aData[0] + '">';
                jqTds[1].innerHTML = '<input type="text" class="form-control small" value="' + aData[1] + '">';
                //jqTds[2].innerHTML = '<input type="text" class="form-control small" value="' + aData[2] + '">';
                jqTds[2].innerHTML =  aData[2];
                jqTds[3].innerHTML = '<input type="text" class="form-control small" value="' + aData[3] + '">';
                jqTds[4].innerHTML = '<a class="edit" href="">Save</a>';
                jqTds[5].innerHTML = '<a class="cancel" href="">Cancel</a>';
            }

            function saveRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                var regex = new RegExp(',', 'g');
                var programDetailTier = new Object();
                console.log(jqInputs[3]);
                programDetailTier.id = "";
                programDetailTier.programDetailId = 42;
                programDetailTier.level=jqInputs[0].value;
                programDetailTier.amount=jqInputs[1].value;
                var radio='<label class="control-label" for="inputSuccess"> <input type="radio" checked="checked" name="optionsRadiosDenomination" id="optionsRadiosPercentageTier1" value="%" class="radio-inline denomination ">%</label>'+
                '<label class="control-label" for="inputSuccess"> <input type="radio" name="optionsRadiosDenomination" id="optionsRadiosPercentageTier1" value="%" class="radio-inline denomination ">$</label>';
                if(jqInputs[2].checked){
                	programDetailTier.tierType=escape(jqInputs[2].value);
                    
                }else{
                	programDetailTier.tierType=escape(jqInputs[3].value);
                	radio='<label class="control-label" for="inputSuccess"> <input type="radio"  name="optionsRadiosDenomination" id="optionsRadiosPercentageTier1" value="%" class="radio-inline denomination ">%</label>'+
                    '<label class="control-label" for="inputSuccess"> <input type="radio" checked="checked" name="optionsRadiosDenomination" id="optionsRadiosPercentageTier1" value="%" class="radio-inline denomination ">$</label>';
                }
                
                programDetailTier.beginRange=jqInputs[4].value.replace(regex, '');
                console.log(programDetailTier);
                
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                //oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                //var val=(jqInputs[2].value+jqInputs[3].value);

                oTable.fnUpdate(radio, nRow, 2, false);
                oTable.fnUpdate(jqInputs[4].value, nRow, 3, false);
                oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
                oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 5, false);
                
                $.ajax({
                    url: '/SalesIncentiveMgmt/v1/program/updateTier/'+JSON.stringify(programDetailTier),
                    type: "GET",
                    dataType: 'json',
                    success: function(data) {
                    }
                 	 });
                oTable.fnDraw();
            }

            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
                oTable.fnDraw();
            }

            var oTable = $('#editable-sample,#tier-schedule').dataTable({
                "aLengthMenu": [
                    [5, 10, 20, -1],
                    [5, 10, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 5,
                "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': true,
                        'aTargets': [0]
                    }
                ]
            });

            jQuery('#tier-schedule_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
            jQuery('#tier-schedule_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

            var nEditing = null;

            $('#tier-schedule_new').click(function (e) {
                e.preventDefault();
                var radio='<label class="control-label" for="inputSuccess"> <input type="radio" checked="checked" name="optionsRadiosDenomination" id="optionsRadiosPercentageTier1" value="%" class="radio-inline denomination ">%</label>'+
                '<label class="control-label" for="inputSuccess"> <input type="radio" name="optionsRadiosDenomination" id="optionsRadiosPercentageTier1" value="%" class="radio-inline denomination ">$</label>';
                var aiNew = oTable.fnAddData(['', '', radio, '',
                        '<a class="edit" href="">Edit</a>', '<a class="cancel" data-mode="new" href="">Cancel</a>'
                ]);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                editRow(oTable, nRow);
                nEditing = nRow;

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
                url: '/SalesIncentiveMgmt/v1/program/removeTier/'+id,
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
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $('#editable-sample a.edit,#tier-schedule a.edit').live('click', function (e) {
                e.preventDefault();

                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];

                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.innerHTML == "Save") {
                    /* Editing this row and want to save it */
                    saveRow(oTable, nEditing);
                    nEditing = null;
                    alert("Updated! Do not forget to do some ajax to sync with backend :)");
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
            });
        }

    };

}();