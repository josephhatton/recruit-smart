(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderDeleteController', JobOrderDeleteController);

  JobOrderDeleteController.$inject = ['$uibModalInstance', 'entity', 'JobOrder'];

  function JobOrderDeleteController($uibModalInstance, entity, JobOrder) {
    var vm = this;

    vm.jobOrder = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      JobOrder.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
