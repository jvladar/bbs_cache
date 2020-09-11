$(document).ready(
    function() {

        // SUBMIT FORM
        $("#bookSearchForm").submit(function(event) {
            // Prevent the form from submitting via the browser.
            event.preventDefault();
            ajaxPost();
        });

        function ajaxPost() {

            var formData = {
                bookId : $("#bookId").val(),
                bookName : $("#bookName").val(),
                author : $("#author").val()
            }
            // DO POST
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : "searchBook",
                data : JSON.stringify(formData),
                dataType : 'json',
                success : function(result) {
                    if (result.status == "success") {

                        $('#getResult ul').empty();
                        var custList = "";
                        $.each(result.data, function(i, book) {
                            var user = "Book Name  "
                                + book.bookName
                                + ", Author  = " + book.author
                                + "<br>"
                                $('#getResult .list-group').append(
                                    user)
                        });
                        console.log("Success: ", result);

                    } else {
                        $("#getResult").html("<strong>Error</strong>");
                        console.log("Fail: ", result);
                    }
                },
                error : function(e) {
                    alert("Error!")
                    console.log("ERROR: ", e);
                }
            });

        }

    })