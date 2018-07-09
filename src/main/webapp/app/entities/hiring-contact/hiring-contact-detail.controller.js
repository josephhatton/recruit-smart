(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactDetailController', HiringContactDetailController);

  HiringContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity',
    'Principal', 'HiringContact', 'Company', 'JobOrder', 'Activity'];

  function HiringContactDetailController($scope, $rootScope, $stateParams, previousState, entity,
                                         Principal, HiringContact, Company, JobOrder, Activity) {
    var vm = this;

    vm.hiringContact = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:hiringContactUpdate', function (event, result) {
      vm.hiringContact = result;
    });
    $scope.$on('$destroy', unsubscribe);

    getAccount();

    function getAccount() {
      Principal.identity().then(function (account) {
        vm.user = account;
        vm.isAuthenticated = Principal.isAuthenticated;
      });
    }

    vm.cancelContactSave = function () {
      vm.contactEditable = false;
    };

    //COMMENTS
    //General Comments
    vm.commentSave = function () {
      vm.hiringContact.hiringContactComments.push({
        comment: vm.hiringContactComment,
        user: vm.user
      });
      HiringContact.update(vm.hiringContact, function (result) {
        vm.hiringContact = result;
      }, onSaveError);

      vm.commentEditable = false;
    };

    vm.cancelComment = function () {
      vm.commentEditable = false;
      vm.compEdit = false;
    };

    vm.removeComment = function (index) {
      vm.hiringContact.hiringContactComments.splice(index, 1);
      HiringContact.update(vm.hiringContact, onSaveSuccess, onSaveError);
    };
    //End of General Comments

    // Internal Comments
    vm.internalCommentSave = function () {
      vm.hiringContact.hiringContactInternalComments.push({
        comment: vm.hiringContactInternalComment,
        user: vm.user
      });
      HiringContact.update(vm.hiringContact, function (result) {
        vm.hiringContact = result;
      }, onSaveError);
      vm.internalCommentEditable = false;
    };

    vm.cancelInternalComment = function () {
      vm.internalCommentEditable = false;
    };

    vm.removeInternalComment = function (index) {
      vm.hiringContact.hiringContactInternalComments.splice(index, 1);
      HiringContact.update(vm.hiringContact, onSaveSuccess, onSaveError);
    };
    // End of Internal Comments

    //END OF COMMENTS

    var onSaveSuccess = function (result) {
      $scope.$emit('recruitsmartApp:hiringContactUpdate', result);
      vm.isSaving = false;
    };

    var onSaveError = function (result) {
      vm.isSaving = false;
      $Slog(result);
    };
  }
})();
