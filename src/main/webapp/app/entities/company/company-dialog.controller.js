(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('CompanyDialogController', CompanyDialogController);

  CompanyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Company', 'CompanyComment', 'Address', 'JobOrder', 'HiringContact', 'CompanyInternalComment', 'Activity', 'Skill'];

  function CompanyDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Company, CompanyComment, Address, JobOrder, HiringContact, CompanyInternalComment, Activity, Skill) {
    var vm = this;

    vm.company = entity;
    vm.clear = clear;
    vm.save = save;
    vm.companycomments = CompanyComment.query();
    vm.addresses = Address.query();
    vm.joborders = JobOrder.query();
    vm.hiringcontacts = HiringContact.query();
    vm.companyinternalcomments = CompanyInternalComment.query();
    vm.activities = Activity.query();
    vm.skills = Skill.query();

    $timeout(function () {
      angular.element('.form-group:eq(1)>input').focus();
    });

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    function save() {
      vm.isSaving = true;
      if (vm.company.id !== null) {
        Company.update(vm.company, onSaveSuccess, onSaveError);
      } else {
        Company.save(vm.company, onSaveSuccess, onSaveError);
      }
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:companyUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    }

    function onSaveError() {
      vm.isSaving = false;
    }


  }
})();
