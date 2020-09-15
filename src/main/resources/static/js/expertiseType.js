
function doStuff() {
    $(document).on("click", ".confirm-delete", function(e) {

        var id = $(this).data('id');
        console.log(id);
        var confirmModal=$('#modal1');

        $('#okButton').click(function () {
            confirmModal.modal('hide');
            document.location.href="/admin/expertiseTypeDelete/"+id;
        });

        confirmModal.modal('show');

    });

}
