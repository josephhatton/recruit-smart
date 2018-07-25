(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('SearchListController', SearchListController);

  SearchListController.$inject = ['$scope', '$state', 'SearchList', 'SearchListSearch', 'ParseLinks', 'AlertService'];

  function SearchListController($scope, $state, SearchList, SearchListSearch, ParseLinks, AlertService) {
    var vm = this;
    vm.searchLists = [];
    vm.predicate = 'id';
    vm.reverse = true;
    vm.page = 0;
    vm.loadAll = function () {
      if (vm.currentSearch) {
        SearchListSearch.query({
          query: vm.currentSearch,
          page: vm.page,
          size: 20,
          sort: sort()
        }, onSuccess, onError);
      } else {
        SearchList.query({
          page: vm.page,
          size: 20,
          sort: sort()
        }, onSuccess, onError);
      }
      function sort() {
        var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
        if (vm.predicate !== 'id') {
          result.push('id');
        }
        return result;
      }

      function onSuccess(data, headers) {
        vm.links = ParseLinks.parse(headers('link'));
        vm.totalItems = headers('X-Total-Count');
        for (var i = 0; i < data.length; i++) {
          vm.searchLists.push(data[i]);
        }
      }

      function onError(error) {
        AlertService.error(error.data.message);
      }
    };
    vm.reset = function () {
      vm.page = 0;
      vm.searchLists = [];
      vm.loadAll();
    };
    vm.loadPage = function (page) {
      vm.page = page;
      vm.loadAll();
    };

    vm.search = function (searchQuery) {
      if (!searchQuery) {
        return vm.clear();
      }
      vm.searchLists = [];
      vm.links = null;
      vm.page = 0;
      vm.predicate = '_score';
      vm.reverse = false;
      vm.currentSearch = searchQuery;
      vm.loadAll();
    };

    vm.clear = function () {
      vm.searchLists = [];
      vm.links = null;
      vm.page = 0;
      vm.predicate = 'id';
      vm.reverse = true;
      vm.searchQuery = null;
      vm.currentSearch = null;
      vm.loadAll();
    };

    vm.loadAll();

  }
})();
