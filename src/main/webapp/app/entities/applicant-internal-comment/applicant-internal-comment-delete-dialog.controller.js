(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantInternalCommentDeleteController', ApplicantInternalCommentDeleteController);

  ApplicantInternalCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'ApplicantInternalComment'];

  function ApplicantInternalCommentDeleteController($uibModalInstance, entity, ApplicantInternalComment) {
    var vm = this;

    vm.applicantInternalComment = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      ApplicantInternalComment.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
