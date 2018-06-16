(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HotListBucketDialogController', HotListBucketDialogController);

  HotListBucketDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HotListBucket'];

  function HotListBucketDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, HotListBucket) {
    var vm = this;

    vm.hotListBucket = entity;
    vm.clear = clear;
    vm.save = save;

    $timeout(function () {
      angular.element('.form-group:eq(1)>input').focus();
    });

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function save() {
      vm.isSaving = true;
      if (vm.hotListBucket.id !== null) {
        HotListBucket.update(vm.hotListBucket, onSaveSuccess, onSaveError);
      } else {
        HotListBucket.save(vm.hotListBucket, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:hotListBucketUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
