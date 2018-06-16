(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('BookDetailController', BookDetailController);

  BookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Book'];

  function BookDetailController($scope, $rootScope, $stateParams, previousState, entity, Book) {
    var vm = this;

    vm.book = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:bookUpdate', function (event, result) {
      vm.book = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();
