(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HotListBucketDeleteController', HotListBucketDeleteController);

  HotListBucketDeleteController.$inject = ['$uibModalInstance', 'entity', 'HotListBucket'];

  function HotListBucketDeleteController($uibModalInstance, entity, HotListBucket) {
    var vm = this;

    vm.hotListBucket = entity;
    vm.clear = clear;
    vm.confirmDelete = confirmDelete;

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function confirmDelete(id) {
      HotListBucket.delete({id: id},
        function () {
          $uibModalInstance.close(true);
        });
    }
  }
})();
