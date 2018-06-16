(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('CompanyDetailController', CompanyDetailController);

  CompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity',
    'Principal', 'Company', 'CompanyJob', 'HiringContact'];

  function CompanyDetailController($scope, $rootScope, $stateParams, previousState, entity,
                                   Principal, Company, CompanyJob, HiringContact) {
    var vm = this;

    vm.company = entity;
    vm.jobOrders = [];
    vm.previousState = previousState.name;

    getAccount();

    var unsubscribe = $rootScope.$on('recruitsmartApp:companyUpdate', function (event, result) {
      vm.company = result;
    });
    $scope.$on('$destroy', unsubscribe);

    function getAccount() {
      Principal.identity().then(function (account) {
        vm.user = account;
        vm.isAuthenticated = Principal.isAuthenticated;
      });
    }

    CompanyJob.query({id: $stateParams.id}, function (result) {
      vm.jobOrders = result;
    }, onSaveError);

    vm.contactSave = function () {
      Company.update(vm.company, function (result) {
        vm.company = result;
      }, onSaveError);
      vm.contactEditable = false;
    };
    vm.cancelContactSave = function () {
      vm.contactEditable = false;
    };

    vm.addAddress = function () {
      vm.address = {
        address1: null,
        address2: null,
        city: null,
        state: null,
        zipCode: null
      };
      vm.company.addresses.push(vm.address);
    };

    vm.removeAddress = function (index) {
      vm.company.addresses.splice(index, 1);
    };

    //COMMENTS
    //================== General Comments ================
    vm.commentSave = function () {
      vm.company.companyComments.push({
        comment: vm.companyComment,
        user: vm.user
      });
      Company.update(vm.company, function (result) {
        vm.company = result;
      }, onSaveError);
      vm.commentEditable = false;
    };

    vm.cancelComment = function () {
      vm.commentEditable = false;
    };

    vm.removeComment = function (index) {
      vm.company.companyComments.splice(index, 1);
      Company.update(vm.company, onSaveSuccess, onSaveError);
    };
    //End of General Comments

    // Internal Comments
    //==================  Internal Comments ================
    vm.internalCommentSave = function () {
      vm.company.companyInternalComments.push({
        comment: vm.companyInternalComment,
        user: vm.user
      });
      Company.update(vm.company, function (result) {
        vm.company = result;
      }, onSaveError);
      vm.internalCommentEditable = false;
    };

    vm.cancelInternalComment = function () {
      vm.internalCommentEditable = false;
    };

    vm.removeInternalComment = function (index) {
      vm.company.companyInternalComments.splice(index, 1);
      Company.update(vm.company, onSaveSuccess, onSaveError);
    };
    // End of Internal Comments

    vm.addSkill = function () {
      //If it's not, then add it to DB, then on completion update applicant
      vm.company.companySkills.push({
        name: vm.newSkill
      });
      Company.update(vm.company, onSaveSuccess);
    };

    vm.deleteSkill = function (index) {
      vm.company.companySkills.splice(index, 1);
      Company.update(vm.company, onSaveSuccess, onSaveError);
    };
    vm.cancelSkill = function () {
      vm.skillsEditable = false;
    };
    var onSaveSuccess = function (result) {
      $scope.$emit('recruitsmartApp:addressUpdate', result);
      vm.isSaving = false;
    };

    var onSaveError = function (result) {
      vm.isSaving = false;
    };

  }
})();
