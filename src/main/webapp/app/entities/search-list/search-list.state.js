(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('search', {
        parent: 'entity',
        url: '/search',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'Search'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/search-list/search.html',
            controller: 'SearchController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('search-list', {
        parent: 'entity',
        url: '/search-list',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'SearchLists'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/search-list/search-lists.html',
            controller: 'SearchListController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      });
  }

})();
