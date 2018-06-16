(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantStatusDeleteController', ApplicantStatusDeleteController);

  ApplicantStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'ApplicantStatus'];

  function ApplicantStatusDeleteController($uibModalInstance, entity, ApplicantStatus) {
    var vm = this;

    vm.applicantStatus = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      ApplicantStatus.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
