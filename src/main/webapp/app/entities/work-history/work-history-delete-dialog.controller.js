(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('WorkHistoryDeleteController', WorkHistoryDeleteController);

  WorkHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkHistory'];

  function WorkHistoryDeleteController($uibModalInstance, entity, WorkHistory) {
    var vm = this;

    vm.workHistory = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      WorkHistory.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
