function doStuff() {
    $(document).on("click", ".confirm-delete-attachment", function(e) {

        var id = $(this).data('id');
        var expertiseId = $(this).data('expertiseid');
        var confirmModal=$('#modal1');

        $('#okButton').click(function () {
            confirmModal.modal('hide');
            document.location.href="/attachmentDelete/"+id+"?expertiseId="+expertiseId;
        });

        confirmModal.modal('show');

    });

}