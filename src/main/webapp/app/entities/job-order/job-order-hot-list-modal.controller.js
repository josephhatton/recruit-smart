(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderHotListModalController', JobOrderHotListModalController);

  JobOrderHotListModalController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Principal', 'HotList', 'HotListBucket'];

  function JobOrderHotListModalController($scope, $stateParams, $uibModalInstance, entity, Principal, HotList, HotListBucket) {
    var vm = this;
    vm.jobOrder = entity;

    vm.userType = "Recruiter";
    vm.exitingBucket = null;
    vm.type = null;
    vm.createdTab = true;

    load();

    function load() {
      //get logged in user.
      Principal.identity().then(function (account) {
        vm.account = account;
        vm.isAuthenticated = Principal.isAuthenticated;
      });

      HotListBucket.query(function (result) {
        vm.bucket = result;
      });

    }

    //changing tabs between 'Create New' and 'Add to Existing'
    vm.tabChange = function (val) {
      vm.createdTab = val
    }

    //Add to existing
    vm.addToExisting = function (oldList) {
      //add to hotList based on type
    }

    vm.save = function () {
      if (vm.createdTab) {
        HotListBucket.save({name: vm.newBucket}, function (result) {
          HotList.save({
            name: 'Job Orders',
            hotListType: 'Job Orders',
            jobOrder: vm.jobOrder,
            hotListBucket: result
          }, onSaveSuccess, onSaveError);
        }, onSaveError);
      } else {
        HotList.save({
          name: 'Job Orders',
          hotListType: 'Job Orders',
          jobOrder: vm.jobOrder,
          hotListBucket: vm.exitingBucket
        }, onSaveSuccess, onSaveError);
      }
    };

    vm.clear = function () {
      $uibModalInstance.dismiss('cancel');
    };

    var onSaveSuccess = function (result) {
      $scope.$emit('recruitSmartApp:workHistoryUpdate', result);
      $uibModalInstance.close(result, vm.new);
      vm.isSaving = false;
    };

    var onSaveError = function () {
      vm.isSaving = false;
    };
  }
})();
