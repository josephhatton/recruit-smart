(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderDialogController', JobOrderDialogController);

  JobOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JobOrder', 'JobOrderComment', 'Address', 'Skill', 'Company', 'JobOrderInternalComment', 'HiringContact', 'Activity'];

  function JobOrderDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, JobOrder, JobOrderComment, Address, Skill, Company, JobOrderInternalComment, HiringContact, Activity) {
    var vm = this;

    vm.jobOrder = entity;
    vm.clear = clear;
    vm.save = save;
    vm.jobordercomments = JobOrderComment.query();
    vm.addresses = Address.query();
    vm.skills = Skill.query();
    vm.companies = Company.query();
    vm.joborderinternalcomments = JobOrderInternalComment.query();
    vm.hiringcontacts = HiringContact.query();
    vm.activities = Activity.query();

    $timeout(function () {
      angular.element('.form-group:eq(1)>input').focus();
    });

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function save() {
      vm.isSaving = true;
      if (vm.jobOrder.id !== null) {
        JobOrder.update(vm.jobOrder, onSaveSuccess, onSaveError);
      } else {
        JobOrder.save(vm.jobOrder, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:jobOrderUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
