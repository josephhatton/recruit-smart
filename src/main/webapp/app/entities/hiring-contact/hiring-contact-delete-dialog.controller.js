(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactDeleteController', HiringContactDeleteController);

  HiringContactDeleteController.$inject = ['$uibModalInstance', 'entity', 'HiringContact'];

  function HiringContactDeleteController($uibModalInstance, entity, HiringContact) {
    var vm = this;

    vm.hiringContact = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      HiringContact.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
