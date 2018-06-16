(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HotListDeleteController', HotListDeleteController);

  HotListDeleteController.$inject = ['$uibModalInstance', 'entity', 'HotList'];

  function HotListDeleteController($uibModalInstance, entity, HotList) {
    var vm = this;

    vm.hotList = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      HotList.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
