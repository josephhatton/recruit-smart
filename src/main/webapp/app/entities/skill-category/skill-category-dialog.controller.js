(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('SkillCategoryDialogController', SkillCategoryDialogController);

  SkillCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SkillCategory'];

  function SkillCategoryDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, SkillCategory) {
    var vm = this;

    vm.skillCategory = entity;
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
      if (vm.skillCategory.id !== null) {
        SkillCategory.update(vm.skillCategory, onSaveSuccess, onSaveError);
      } else {
        SkillCategory.save(vm.skillCategory, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:skillCategoryUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
