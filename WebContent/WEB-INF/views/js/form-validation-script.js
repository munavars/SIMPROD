var Script = function () {

    $.validator.setDefaults({
        submitHandler: function() { alert("Submitted!"); location.href = 'index.html'; }
    });

    $().ready(function() {

        // validate signup form on keyup and submit
        $("#signupForm").validate({
            rules: {
                username: "required",
                password: "required",
                username: {
                    required: true,
                    minlength: 2
                },
                password: {
                    required: true,
                    minlength: 5
                },
                
               
            },
            messages: {
                username: {
                    required: "Please enter a username",
                    minlength: "Your username must consist of at least 2 characters"
                },
                password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                }
                
            }
        });
    });


}();