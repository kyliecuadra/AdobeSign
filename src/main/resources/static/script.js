$(document).ready(function () {
    // Send Document
    $("#uploadForm").submit(function (event) {
        event.preventDefault();

        let fileInput = $("#pdfFile")[0].files[0];
        let emailInput = $("#recipientEmail").val();

        if (!fileInput || !emailInput) {
            $("#uploadStatus").html('<div class="alert alert-danger">Please fill in all fields.</div>');
            return;
        }

        let formData = new FormData();
        formData.append("file", fileInput);
        formData.append("recipientEmail", emailInput);

        $.ajax({
            url: "http://localhost:8080/adobe-sign/send",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function () {
                $("#uploadStatus").html('<div class="alert alert-warning">Sending document...</div>');
            },
            success: function (data) {
                $("#uploadStatus").html('<div class="alert alert-success">Document sent successfully! Agreement ID: ' + data.agreementId + '</div>');
                $("#agreementId").val(data.agreementId);
            },
            error: function () {
                $("#uploadStatus").html('<div class="alert alert-danger">Failed to send document.</div>');
            }
        });
    });

    // Check Document Status
    $("#checkStatusBtn").click(function () {
        let agreementId = $("#agreementId").val();
        if (!agreementId) {
            $("#statusResult").html('<div class="alert alert-danger">Please enter an Agreement ID.</div>').removeClass("d-none");
            return;
        }

        $.ajax({
            url: "http://localhost:8080/adobe-sign/status/" + agreementId,
            type: "GET",
            beforeSend: function () {
                $("#statusResult").html("Checking status...").removeClass("d-none alert-success alert-danger").addClass("alert-info");
            },
            success: function (data) {
                $("#statusResult").html("Status: " + data.status).removeClass("alert-info alert-danger").addClass("alert-success");
            },
            error: function () {
                $("#statusResult").html("Failed to fetch status.").removeClass("alert-info alert-success").addClass("alert-danger");
            }
        });
    });
});
