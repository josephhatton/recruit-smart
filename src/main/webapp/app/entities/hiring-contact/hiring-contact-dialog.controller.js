(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactDialogController', HiringContactDialogController);

  HiringContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HiringContact', 'Company', 'HiringContactComment', 'HiringContactInternalComment', 'JobOrder', 'Activity'];

  function HiringContactDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, HiringContact, Company, HiringContactComment, HiringContactInternalComment, JobOrder, Activity) {
    var vm = this;

    vm.hiringContact = entity;
    vm.clear = clear;
    vm.save = save;
    vm.companies = Company.query();
    vm.hiringcontactcomments = HiringContactComment.query();
    vm.hiringcontactinternalcomments = HiringContactInternalComment.query();
    vm.joborders = JobOrder.query();
    vm.activities = Activity.query();

    $timeout(function () {
      angular.element('.form-group:eq(1)>input').focus();
    });

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function save() {
      vm.isSaving = true;
      if (vm.hiringContact.id !== null) {
        HiringContact.update(vm.hiringContact, onSaveSuccess, onSaveError);
      } else {
        HiringContact.save(vm.hiringContact, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:hiringContactUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
