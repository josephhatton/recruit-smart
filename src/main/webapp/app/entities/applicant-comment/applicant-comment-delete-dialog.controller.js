(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantCommentDeleteController', ApplicantCommentDeleteController);

  ApplicantCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'ApplicantComment'];

  function ApplicantCommentDeleteController($uibModalInstance, entity, ApplicantComment) {
    var vm = this;

    vm.applicantComment = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      ApplicantComment.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
