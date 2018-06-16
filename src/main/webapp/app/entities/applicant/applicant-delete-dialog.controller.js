(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantDeleteController', ApplicantDeleteController);

  ApplicantDeleteController.$inject = ['$uibModalInstance', 'entity', 'Applicant'];

  function ApplicantDeleteController($uibModalInstance, entity, Applicant) {
    var vm = this;

    vm.applicant = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      Applicant.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
