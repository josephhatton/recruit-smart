(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HotListDialogController', HotListDialogController);

  HotListDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HotList'];

  function HotListDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, HotList) {
    var vm = this;

    vm.hotList = entity;
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
      if (vm.hotList.id !== null) {
        HotList.update(vm.hotList, onSaveSuccess, onSaveError);
      } else {
        HotList.save(vm.hotList, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:hotListUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
