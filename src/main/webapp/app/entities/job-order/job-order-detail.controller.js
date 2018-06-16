(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderDetailController', JobOrderDetailController);

  JobOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'Principal', 'entity', 'JobOrder', 'Address', 'Company', 'HiringContact', 'Activity'];

  function JobOrderDetailController($scope, $rootScope, $stateParams, previousState, Principal, entity, JobOrder, Address, Company, HiringContact, Activity) {
    var vm = this;

    vm.jobOrder = entity;
    vm.previousState = previousState.name;

    getAccount();

    function getAccount() {
      Principal.identity().then(function (account) {
        vm.user = account;
        vm.isAuthenticated = Principal.isAuthenticated;
      });
    }

    var unsubscribe = $rootScope.$on('recruitsmartApp:jobOrderUpdate', function (event, result) {
      vm.jobOrder = result;
    });
    $scope.$on('$destroy', unsubscribe);

    vm.cancelInfo = function () {
      vm.infoEditable = false;
    };

    vm.cancelContactSave = function () {
      vm.contactEditable = false;
    };

    //COMMENTS
    //General Comments
    vm.commentSave = function () {
      vm.jobOrder.jobOrderComments.push({
        comment: vm.jobOrderComment,
        user: vm.user
      });
      JobOrder.update(vm.jobOrder, function (result) {
        vm.jobOrder = result;
      }, onSaveError);

      vm.commentEditable = false;
    };

    vm.cancelComment = function () {
      vm.commentEditable = false;
      vm.compEdit = false;
    };

    vm.removeComment = function (index) {
      vm.jobOrder.jobOrderComments.splice(index, 1);
      JobOrder.update(vm.jobOrder, onSaveSuccess, onSaveError);
    };
    //End of General Comments

    // Internal Comments
    vm.internalCommentSave = function () {
      vm.jobOrder.jobOrderInternalComments.push({
        comment: vm.jobOrderInternalComment,
        user: vm.user
      });
      JobOrder.update(vm.jobOrder, function (result) {
        vm.jobOrder = result;
      }, onSaveError);
      vm.internalCommentEditable = false;
    };

    vm.cancelInternalComment = function () {
      vm.internalCommentEditable = false;
    };

    vm.removeInternalComment = function (index) {
      vm.jobOrder.jobOrderInternalComments.splice(index, 1);
      JobOrder.update(vm.jobOrder, onSaveSuccess, onSaveError);
    };
    // End of Internal Comments
    //END OF COMMENTS

    var onSaveSuccess = function (result) {
      $scope.$emit('recruitsmartApp:jobOrderUpdate', result);
      vm.isSaving = false;
    };

    var onSaveError = function (result) {
      vm.isSaving = false;
      $Slog(result);
    };

    vm.addSkill = function () {
      //If it's not, then add it to DB, then on completion update applicant
      vm.jobOrder.skills.push({
        name: vm.newSkill
      });
      JobOrder.update(vm.jobOrder, onSaveSuccess);
    };

    vm.deleteSkill = function (index) {
      vm.jobOrder.skills.splice(index, 1);
      JobOrder.update(vm.jobOrder, onSaveSuccess, onSaveError);
    };
    vm.cancelSkill = function () {
      vm.skillsEditable = false;
    };

  }
})();
