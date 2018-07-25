(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('SearchListDialogController', SearchListDialogController);

  SearchListDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SearchList', 'User'];

  function SearchListDialogController($scope, $stateParams, $uibModalInstance, entity, SearchList, User) {
    var vm = this;
    vm.searchList = entity;
    vm.users = User.query();
    vm.load = function (id) {
      SearchList.get({id: id}, function (result) {
        vm.searchList = result;
      });
    };

    var onSaveSuccess = function (result) {
      $scope.$emit('recruitSmartApp:searchListUpdate', result);
      $uibModalInstance.close(result);
      vm.isSaving = false;
    };

    var onSaveError = function () {
      vm.isSaving = false;
    };

    vm.save = function () {
      vm.isSaving = true;
      if (vm.searchList.id !== null) {
        SearchList.update(vm.searchList, onSaveSuccess, onSaveError);
      } else {
        SearchList.save(vm.searchList, onSaveSuccess, onSaveError);
      }
    };

    vm.clear = function () {
      $uibModalInstance.dismiss('cancel');
    };
  }
})();
