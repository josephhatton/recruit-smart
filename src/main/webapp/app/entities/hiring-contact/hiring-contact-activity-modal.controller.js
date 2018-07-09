(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactActivityModalController', HiringContactActivityModalController);

  HiringContactActivityModalController.$inject = ['$scope','$rootScope', '$stateParams', 'Principal', '$uibModalInstance', 'HiringContact', 'entity',
    'ActivityAction'];

  function HiringContactActivityModalController($scope, $rootScope, $stateParams, Principal, $uibModalInstance, HiringContact, entity,
                                            ActivityAction) {
    var vm = this;

    vm.hiringContact = entity;
    vm.activityActions = null;
    vm.activity = {};

    load();

    function load() {
      Principal.identity().then(function (account) {
        vm.account = account;
        vm.isAuthenticated = Principal.isAuthenticated;
      });
      vm.activityActions = ActivityAction.query();
    }

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    vm.save = function () {
      vm.hiringContact.activities.push(vm.activity)
      HiringContact.update(vm.hiringContact, onSaveSuccess, onSaveError);
    };

    vm.clear = function () {
      $uibModalInstance.dismiss('cancel');
    };

    var onSaveSuccess = function (result) {
      $rootScope.$broadcast('recruitSmartApp:hiringContactUpdate',{data:result});
      $uibModalInstance.close(result);
    };
    var onSaveError = function () {
    };

  }
})();
