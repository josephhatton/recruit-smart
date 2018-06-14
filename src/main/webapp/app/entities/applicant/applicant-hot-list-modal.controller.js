(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('ApplicantHotListModalController', ApplicantHotListModalController);

  ApplicantHotListModalController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity','Principal', 'HotList', 'HotListBucket'];

  function ApplicantHotListModalController($scope, $stateParams, $uibModalInstance, entity, Principal, HotList, HotListBucket) {
    var vm = this;
    vm.applicant = entity;

    vm.userType = "Recruiter";
    vm.exitingBucket = null;
    vm.type = null;
    vm.new = true;

      load();

      function load() {
          //get logged in user.
          Principal.identity().then(function(account) {
              vm.account = account;
              vm.isAuthenticated = Principal.isAuthenticated;
          });

          HotListBucket.query(function(result) {
              vm.bucket = result;
          });

      }

    //changing tabs between 'Create New' and 'Add to Existing'
    vm.tabChange = function () {
    }

    //Add to existing
    vm.addToExisting = function (oldList) {
        //add to hotList based on type
    }

    vm.save = function () {
        if (vm.new) {
            vm.hotList = {
                applicant:  vm.applicant,
                hotListBucket:  vm.newBucket
            };
            HotListBucket.save({name: vm.newBucket}, function (result) {
                vm.hotList.hotListBucket = vm.newBucket;
            }, onSaveError);
        } else {
            vm.hotList = {
                applicant:  vm.applicant,
                hotListBucket: vm.exitingBucket
            };
        }
          HotList.save(vm.hotList, onSaveSuccess, onSaveError);
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
