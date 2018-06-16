(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ActivityActionDeleteController', ActivityActionDeleteController);

  ActivityActionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ActivityAction'];

  function ActivityActionDeleteController($uibModalInstance, entity, ActivityAction) {
    var vm = this;

    vm.activityAction = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      ActivityAction.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
