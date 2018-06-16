(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('WorkStatusDeleteController', WorkStatusDeleteController);

  WorkStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkStatus'];

  function WorkStatusDeleteController($uibModalInstance, entity, WorkStatus) {
    var vm = this;

    vm.workStatus = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      WorkStatus.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
